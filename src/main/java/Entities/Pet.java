package Entities;



import com.github.javafaker.FunnyName;
import com.github.javafaker.Name;

//==================================================================================================================
//                                          | CLASS PET |
//==================================================================================================================


public class Pet {

    private String name;
    public String url;
    public String status;

    public Pet(FunnyName funnyName) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }
    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }


    private Integer id;

    public Pet(String name, Integer id, String url, String status){

        this.name = name;
        this.id = id;
        this.url = url;
        this.status = status;
    }

}
