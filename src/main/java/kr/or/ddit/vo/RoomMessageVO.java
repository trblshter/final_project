package kr.or.ddit.vo;

import kr.or.ddit.enumpkg.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomMessageVO {

    private String roomId;
    private String writer;
    private String message;
    private MessageType type;
}
