package kr.or.ddit.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CalendarVO implements Serializable{
	private String _id;
	private String title;
	private String description;
	private String start;
	private String end;
	private String type;
	private String username;
	private String backgroundColor;
	private String textColor;
	private boolean allDay;
}
