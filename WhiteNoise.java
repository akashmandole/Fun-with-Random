import java.util.List;


public class WhiteNoise {
	String[] streamdata;
	int size;
	public WhiteNoise(List<String> list) {
		streamdata = new String[list.size()];
		size=list.size();
		int i=0;
		for(String l:list){
			streamdata[i++]=l;
		}}
}
