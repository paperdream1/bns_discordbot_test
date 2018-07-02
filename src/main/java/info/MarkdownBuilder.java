package info;

public class MarkdownBuilder {
	
	StringBuilder message;
	
	public MarkdownBuilder() {
		message = new StringBuilder();
	}
	
	public <T> MarkdownBuilder(T message) {
		this.message = new StringBuilder();
		this.message.append(message);
	}
	
	public String getMessage() {
		return "```" + message.toString() + "```";
	}
	
	public String toString() {
		return message.toString();
	}
	
	public <T> MarkdownBuilder append(T t) {
		message.append(t);
		return this;
	}
	
	public MarkdownBuilder addLine() {
		message.append("-------------------------------------------------------------- \n");
		return this;
	}
	
	public MarkdownBuilder newLine() {
		message.append("\n");
		return this;
	}
	
	

}
