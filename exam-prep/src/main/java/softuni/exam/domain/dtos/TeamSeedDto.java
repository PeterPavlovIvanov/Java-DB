package softuni.exam.domain.dtos;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedDto {
    @Expose
    @XmlElement
    private String name;
    @Expose
    @XmlElement
    private PictureSeedDto picture;

    public TeamSeedDto() {
    }

    @Length(min = 3,max = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public PictureSeedDto getPictureSeedDto() {
        return picture;
    }

    public void setPictureSeedDto(PictureSeedDto picture) {
        this.picture = picture;
    }
}
