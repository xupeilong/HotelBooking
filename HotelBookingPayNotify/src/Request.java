import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "test_url")
public class Request {
	
	@Id
	private int id;
	
	@Column(name = "url")
	private String url;
	
	public Request() {
	}
	
	public Request(String url) {
		super();
		this.url = url;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	

}
