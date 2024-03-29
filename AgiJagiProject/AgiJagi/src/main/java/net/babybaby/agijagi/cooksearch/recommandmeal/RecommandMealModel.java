package net.babybaby.agijagi.cooksearch.recommandmeal;

import java.util.ArrayList;

/**
 * Created by FlaShilver on 2013. 10. 19..
 */
public class RecommandMealModel {

    public static String selectid;
    public String recommandHeader;
    public String date;
    public ArrayList<IdnName> idnNames;

    public RecommandMealModel()
    {
        this.idnNames = new ArrayList<IdnName>();
    }

    public String getRecommandHeader() {
        return recommandHeader;
    }

    public void setRecommandHeader(String recommandHeader) {
        this.recommandHeader = recommandHeader;
    }

    public String getSelectid() {
        return selectid;
    }

    public void setSelectid(String selectid) {
        this.selectid = selectid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
       this.date = date;
    }

    public ArrayList<IdnName> getIdnNames() {
        return idnNames;
    }

    public void setIdnNames(String id, String name) {
        this.idnNames.add(new IdnName(id, name));
    }

    public class IdnName {
        public String id;
        public String name;

        private IdnName(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}