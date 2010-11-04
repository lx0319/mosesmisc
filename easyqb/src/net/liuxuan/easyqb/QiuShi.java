package net.liuxuan.easyqb;

//~--- non-JDK imports --------------------------------------------------------

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Text;

/**
 * Class description
 *
 *
 * @version        Enter version here..., 10/07/17
 * @author         Moses    
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class QiuShi {
    @Persistent
    private Text   Content;
    @Persistent
    private String QB_Id;
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long   id;

    /**
     * Constructs ...
     *
     *
     * @param _QB_Id
     * @param _Content
     */
    public QiuShi(String _QB_Id, String _Content) {
	QB_Id   = _QB_Id;
	Content = new Text(_Content);

	// TODO Auto-generated constructor stub
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getQB_Id() {
	return QB_Id;
    }

    public void setQB_Id(String qBId) {
	QB_Id = qBId;
    }

    public Text getContent() {
	return Content;
    }

    public void setContent(Text content) {
	Content = content;
    }
}


