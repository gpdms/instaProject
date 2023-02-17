package com.study.springboot.post.img;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "post_img")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class PostImgEntity {
	@Id
	@Column(name="img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imgId;
	
	@Column(name="post_id", nullable = false)
	private Integer postId;
	@Column(name="origin_name", nullable = false, length=260)
	private String originName;
	@Column(name="save_name",nullable = false, length=40)
	private String saveName;
	@Column(name="save_path", nullable = false, length=80)
	private String savePath;
	
	@Column(name="delete_yn", nullable=false, columnDefinition = "char(1) default 'n'")
	private String deleteYn; //삭제여부 
	@Column(name="in_time", nullable=false, updatable = false, columnDefinition = "timestamp default current_timestamp")
	private Timestamp inTime; //등록일 
	@Column(name="del_time", columnDefinition = "timestamp on update current_timestamp")
	private Timestamp delTime; //삭제일
	
	@PrePersist
	public void createDefault() {
		this.deleteYn = "n";
		this.inTime = Timestamp.valueOf(LocalDateTime.now());
	}

}
