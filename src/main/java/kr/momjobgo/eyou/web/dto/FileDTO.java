package kr.momjobgo.eyou.web.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * FileVO 파일업로드를 위한 VO
 * fileKey : 파일정보에 대한 pk
 * parentKey : 그 파일을 가지고 있는 글에 대한 pk
 * UpdownloadUtils 와 같이 써야함
 * 
 * upload
 * 먼저 글을 등록하고
 * 그 글의 pk를 가져온담에 
 * upload 시키면 file 리스트가 나옴
 * 그 파일리스트를 db파일리스트에 넣어주면 된다.!
 * @author 오준혁
 *
 */
@Data
public class FileDTO {
	private Integer fileKey; 
	private Integer parentKey;
	private String path;
	private String fileRealName;
	private String fileSaveName;
	private String contentType;
	private String status;
	@DateTimeFormat private Date insertDate;
	@DateTimeFormat private Date deleteDate;
}
