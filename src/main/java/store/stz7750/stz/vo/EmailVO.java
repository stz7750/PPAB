package store.stz7750.stz.vo;


import lombok.Data;

@Data
public class EmailVO {
    private String subject;
    private String content;
    private String date;
    private String receiver;
}
