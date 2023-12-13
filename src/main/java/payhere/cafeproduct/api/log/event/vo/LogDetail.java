package payhere.cafeproduct.api.log.event.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import payhere.cafeproduct.global.enums.LogType;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LogDetail {
    private Long id;
    private LogType logType;
    private String log;
    private String logData;
    private Integer userId;
    private LocalDateTime createdDate;
}
