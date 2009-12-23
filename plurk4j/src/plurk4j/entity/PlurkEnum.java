package plurk4j.entity;

/***
 * Plurk Enums
 * @author afat613@gmail.com
 * @since 2009/12/27
 * @version 0.1.1
 */
public abstract class PlurkEnum {
	public enum PlurkGender {
		MALE("male",1),FEMALE("female",0);

		private String description;
		private Integer source;

		PlurkGender(String description,Integer source) {
			this.description = description;
			this.source = source;
		}

		public String toString() {
			return description;
		}
		
		public Integer getSource() {
			return source;
		}
	}
	public enum PlurkPrivacy {
		WORLD("world"), ONLY_FRIENDS("only_friends"), ONLY_ME("only_me");

		private String description;

		PlurkPrivacy(String description) {
			this.description = description;
		}

		public String toString() {
			return description;
		}
	}
	public enum PlurkQualifier {
		LOVES("loves"), LIKES("likes"), SHARES("shares")
		,GIVES("gives"), HATES("hates"), WANTS("wants")
		,HAS("has"), WILL("will"), ASKS("asks")
		,WISHES("wishes"), WAS("was"), FEELS("feels")
		,THINKS("thinks"), SAYS("says"), IS("is")
		,COLON(":"), FREESTYLE("freestyle"), HOPES("hopes")
		,NEEDS("needs"), WONDERS("wonders");

		private String description;

		PlurkQualifier(String description) {
			this.description = description;
		}

		public String toString() {
			return description;
		}
	}
	public enum PlurkRelationship {
		NOT_SAYING("not_saying"), SINGLE("single"), MARRIED("married")
		,DIVORCED("divorced"), ENGAGED("engaged"), IN_RELATIONSHIP("in_relationship")
		,COMPLICATED("complicated"), WIDOWED("widowed"), OPEN_RELATIONSHIP("open_relationship");
		private String description;
		PlurkRelationship(String description) {
			this.description = description;
		}
		public String toString() {
			return description;
		}
	}
	public enum PlurkIsUnread {
		READ("read",0),UNREAD("unread",1),MUTED("muted",2);

		private String description;
		private Integer source;

		PlurkIsUnread(String description,Integer source) {
			this.description = description;
			this.source = source;
		}

		public String toString() {
			return description;
		}
		
		public Integer getSource() {
			return source;
		}
	}
	public enum PlurkType {
		PUBLIC("public plurk",0)
		,PRIVATE("private plurk",1)
		,LOGGED_IN_PUBLIC("public plurk (responded by the logged in user)",2)
		,LOGGED_IN_PRIVATE("private plurk (responded by the logged in user)",3);

		private String description;
		private Integer source;

		PlurkType(String description,Integer source) {
			this.description = description;
			this.source = source;
		}

		public String toString() {
			return description;
		}
		
		public Integer getSource() {
			return source;
		}
	}
	public enum PlurkNoComments {
		DISABLED("disabled",1)
		,ONLY_FRIENDS("only_friends",2);

		private String description;
		private Integer source;

		PlurkNoComments(String description,Integer source) {
			this.description = description;
			this.source = source;
		}

		public String toString() {
			return description;
		}
		
		public Integer getSource() {
			return source;
		}
	}
}
