package Io;

public class IOPacket {
	public String out;
	public String in;
	public boolean moreInputNecessary;

	public void input(String in) {
		this.in = in;
	}

	public void output(String out, boolean moreInputNecessary) {
		this.out = out;
		this.moreInputNecessary = moreInputNecessary;
	}
}
