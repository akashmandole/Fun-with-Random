import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;

import org.apache.http.client.ClientProtocolException;

import com.marsG.simplerandomorglib.RandomIntegerClient;

public class Fun_with_Random {

	RandomIntegerClient cl;
	
	public Fun_with_Random(RandomIntegerClient cl) {
		this.cl=cl;
	}
	
	RGB_Pixel picture[][];
	final int height = 128;
	final int width = 128;

	RsaKey key;
	final int keySize = 1024;
	
	WhiteNoise whiteNoise;
	final int noiseSize = 2084;

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		RandomIntegerClient cl = new RandomIntegerClient("youremail@foo.bar");
		Fun_with_Random fwr = new Fun_with_Random(cl); 
		fwr.picture=fwr.generateRGBbitmapPicture();
		fwr.displayRGBbitmapPicture();
		
	}

	public RGB_Pixel[][] generateRGBbitmapPicture()
			throws ClientProtocolException, IOException {
		RGB_Pixel[][] picture = new RGB_Pixel[128][128];

		List<String> r = cl.getRandomIntDecimal(0, 256, height * width);
		List<String> g = cl.getRandomIntDecimal(0, 256, height * width);
		List<String> b = cl.getRandomIntDecimal(0, 256, height * width);

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				picture[i][j] = new RGB_Pixel(r.get(i * width + j), g.get(i
						* width + j), b.get(i * width + j));
			}
		}
		return picture;
	}
	
	public WhiteNoise generateWhiteNoise() throws ClientProtocolException, IOException{
		List<String> list = cl.getRandomIntDecimal(0, 256, noiseSize);
		WhiteNoise wn = new WhiteNoise(list);
		return wn;
		
	}

	public RsaKey generateRSAkey() throws ClientProtocolException, IOException {

		int size = keySize;
		boolean foundPrime = false;
		String value="0";
		while(foundPrime==false){
			List<String> list = cl.getRandomIntDecimal(0, size, size);
			for(String l:list){
				if(checkPrime(Integer.parseInt(l))){
					value=l;
					foundPrime=true;
					break;
				}
			}
		}
		
		BigInteger p = new BigInteger(value);
		BigInteger q = p.nextProbablePrime();
		BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
				.subtract(BigInteger.ONE));
		BigInteger e = getCoprime(m);
		BigInteger d = e.modInverse(m);

		RsaKey key = new RsaKey(String.valueOf(d), String.valueOf(e));
		return key;
	}
	
	public boolean checkPrime(int num) {
		for(int i=1;i<=Math.sqrt(num);i++){
			if(num%i==0){
				return false;
			}
		}
		return true;
	}

	public BigInteger getCoprime(BigInteger m) {
		Random rnd = new Random();
		int length = m.bitLength() - 1;
		BigInteger e = BigInteger.probablePrime(length, rnd);
		while (!(m.gcd(e)).equals(BigInteger.ONE)) {
			e = BigInteger.probablePrime(length, rnd);
		}
		return e;
	}

	public void displayRGBbitmapPicture() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(picture[i][j].r + "," + picture[i][j].g + ","
						+ picture[i][j].b + " ");
			}
			System.out.println();
		}
	}
}
