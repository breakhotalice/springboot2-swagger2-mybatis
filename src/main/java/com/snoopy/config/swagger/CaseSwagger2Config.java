package com.snoopy.config.swagger;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

/**
 * 
 * ClassName: CaseSwagger2Config <br/>   
 * Function: Swagger2-Group. <br/>     
 * date: 2018年7月23日 上午10:18:01 <br/>    
 * @author LiHaiqing
 */
@Configuration
@ComponentScan(basePackages = {"com.snoopy.controller.casemodule"})
public class CaseSwagger2Config {

    @Bean
    public Docket caseDocket() {
        final List<ResponseMessage> globalResponses = Arrays.asList(
            new ResponseMessageBuilder()
                .code(200)
                .message("OK")
                .build(),
            new ResponseMessageBuilder()
                .code(400)
                .message("Bad Request")
                .build(),
            new ResponseMessageBuilder()
                .code(500)
                .message("Internal Error")
                .build());
        final ApiInfo apiInfo = new ApiInfoBuilder()
            .title("案件API")
            .description("案件相关API")
            .version("version")
            .license("license")
            .licenseUrl("licenseUrl")
            .contact(
                new Contact(
                    "LiHaiQing",
                    "http://www.gringuy.com",
                    "986619781@qq.com"))
            .termsOfServiceUrl("服务条款URL")// 服务条款URL
            .build();
        Docket docketForBuilder = new Docket(DocumentationType.SWAGGER_2)
            .groupName("case-api")
            .apiInfo(apiInfo)
            .directModelSubstitute(LocalDate.class, String.class)
            .genericModelSubstitutes(ResponseEntity.class)
            .alternateTypeRules(
                newRule(typeResolver.resolve(DeferredResult.class,
                    typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                    typeResolver.resolve(WildcardType.class)))
            .useDefaultResponseMessages(false)
            .globalResponseMessage(RequestMethod.GET, globalResponses)
            .globalResponseMessage(RequestMethod.POST, globalResponses)
            .globalResponseMessage(RequestMethod.DELETE, globalResponses)
            .globalResponseMessage(RequestMethod.PATCH, globalResponses)
            .securitySchemes(newArrayList(apiKey()))
            .securityContexts(newArrayList(securityContext()))
            .enableUrlTemplating(false)
            .globalOperationParameters(
                newArrayList(
                    new ParameterBuilder()
                        .name("someGlobalParameter")
                        .description("Description of someGlobalParameter")
                        .modelRef(new ModelRef("string"))
                        .parameterType("query")
                        .required(false)
                        .build()));

        Docket docket = docketForBuilder
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.snoopy.controller.casemodule"))
            .paths(
                Predicates.and(
                    Predicates.not(Predicates.or(caseBasePaths())),
                    Predicates.or(caseExcludePath())))
            .build()
            .pathMapping("/");
        return docket;
    }

    private List<Predicate<String>> caseBasePaths() {
        List<Predicate<String>> basePath = new ArrayList<>();
        basePath.add(PathSelectors.regex("/user.*"));
        return basePath;
    }

    private List<Predicate<String>> caseExcludePath() {
        List<Predicate<String>> excludePath = new ArrayList<>();
        excludePath.add(PathSelectors.ant("/**"));
        return excludePath;
    }

    @Bean
    public ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfoBuilder()
            .title("snoopy-title")
            .description("snoopy-description")
            .version("snoopy-version")
            .license("snoopy-license")
            .licenseUrl("snoopy-licenseUrl")
            .contact(
                new Contact(
                    "snoopy",
                    "http://www.gringuy.com",
                    "986619781@qq.com"))
            .termsOfServiceUrl("服务条款URL")// 服务条款URL
            .build();
        return apiInfo;
    }

    @Autowired
    private TypeResolver typeResolver;

    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .forPaths(PathSelectors.regex("/anyPath.*"))
            .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
            new SecurityReference("mykey", authorizationScopes));
    }

    @Bean
    SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
            .clientId("test-app-client-id")
            .clientSecret("test-app-client-secret")
            .realm("test-app-realm")
            .appName("test-app")
            .scopeSeparator(",")
            .additionalQueryStringParams(null)
            .useBasicAuthenticationWithAccessCodeGrant(false)
            .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return UiConfigurationBuilder.builder()
            .deepLinking(true)
            .displayOperationId(false)
            .defaultModelsExpandDepth(1)
            .defaultModelExpandDepth(1)
            .defaultModelRendering(ModelRendering.EXAMPLE)
            .displayRequestDuration(false)
            .docExpansion(DocExpansion.NONE)
            .filter(false)
            .maxDisplayedTags(null)
            .operationsSorter(OperationsSorter.ALPHA)
            .showExtensions(false)
            .tagsSorter(TagsSorter.ALPHA)
            .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
            .validatorUrl(null)
            .build();
    }

}