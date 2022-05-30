package daangnmarket.daangn.project.dto;
import daangnmarket.daangn.project.dto.utils.EnumManager;
import lombok.Data;

@Data
public class EnumDTO {
    private String key;
    private String value;

    public EnumDTO(EnumManager e){
        this.key = e.getKey();
        this.value = e.getValue();
    }
}
