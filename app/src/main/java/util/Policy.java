package util;

public class Policy {
    private static Boolean sefareshat,prodect,fani,bazargani,forosh;

    public static void setSefareshat(String val){
        if (val.equals("0")){
            sefareshat=false;
        }else if (val.equals("1")){
            sefareshat=true;
        }
    }
    public static void setProdect(String val){
        if (val.equals("0")){
            prodect=false;
        }else if (val.equals("1")){
            prodect=true;
        }
    }
    public static void setfani(String val){
        if (val.equals("0")){
            fani=false;
        }else if (val.equals("1")){
            fani=true;
        }
    }
    public static void setbazargani(String val){
        if (val.equals("0")){
            bazargani=false;
        }else if (val.equals("1")){
            bazargani=true;
        }
    }
    public static void setforosh(String val){
        if (val.equals("0")){
            forosh=false;
        }else if (val.equals("1")){
            forosh=true;
        }
    }

    public static Boolean getSefareshat() {
        return sefareshat;
    }

    public static Boolean getProdect() {
        return prodect;
    }

    public static Boolean getFani() {
        return fani;
    }

    public static Boolean getBazargani() {
        return bazargani;
    }

    public static Boolean getForosh() {
        return forosh;
    }
}
