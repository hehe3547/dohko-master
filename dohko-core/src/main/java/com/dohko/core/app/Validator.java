package com.dohko.core.app;

import com.dohko.core.PlatResultInfo;
import com.dohko.core.base.DataMap;
import com.dohko.core.base.ResultInfo;

/**
 * Created by xiangbin on 2016/7/16.
 */
public interface Validator {

    ResultInfo SUCCESS = PlatResultInfo.SUCCESS;

    ResultInfo validate(Rule rule, String key, DataMap dataMap, String params);

    enum Rule {
        REQUIRED(false),
        INT(false),
        MIN(true),
        MAX(true);
        private boolean variable;
        Rule(boolean variable) {
            this.variable = variable;
        }
        public boolean variable() {
            return this.variable;
        }
    }
}
