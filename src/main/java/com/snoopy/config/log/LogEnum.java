package com.snoopy.config.log;

/**
 * ClassName: LogEnum <br/>
 * Function: 本地日志枚举. <br/>
 * date: 2018年7月23日 上午10:21:28 <br/>
 * 
 * @author LiHaiqing
 */
public enum LogEnum {

                     BUSSINESS("bussiness"),

                     PLATFORM("platform"),

                     DB("db"),

                     EXCEPTION("exception"),

    ;

    private String category;

    LogEnum(String category){
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
