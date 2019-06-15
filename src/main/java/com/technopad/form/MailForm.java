package com.technopad.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class MailForm {
	@NotNull
	@Size(min=1,max=255,message="メールアドレスは{min}~{max}文字です")
//	@Email(message="メールアドレスの形式ではありません")
//	@Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$", message="メールアドレスの形式ではありません")
	@Pattern(regexp = "^([\\w])+([\\w\\._-])*\\@([\\w])+([\\w\\._-])*\\.([a-zA-Z])+$", message="メールアドレスの形式ではありません")
	private String mailaddress;
}
