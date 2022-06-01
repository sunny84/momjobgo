package kr.momjobgo.eyou.web.jpa.entity;

import kr.momjobgo.eyou.web.jpa.entity.base.BaseTimeEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="FILE")
@EntityListeners(AuditingEntityListener.class)
public class FileEntity extends BaseTimeEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "contents_id")
	private Long contentsId;

	@Column(name = "path")
	private String path;

	@Column(name = "file_real_name")
	private String fileRealName;

	@Column(name = "file_save_name")
	private String fileSaveName;

	@Column(name = "content_type")
	private String contentType;

}
