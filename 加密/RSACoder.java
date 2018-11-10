import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
 
import javax.crypto.Cipher;
 
public class RSACoder {
	//�ǶԳ���Կ�㷨  
    public static final String KEY_ALGORITHM="RSA";  
    /** 
     * ��Կ���ȣ�DH�㷨��Ĭ����Կ������1024 
     * ��Կ���ȱ�����64�ı�������512��65536λ֮�� 
     * */  
    private static final int KEY_SIZE=512;  
    //��Կ  
    private static final String PUBLIC_KEY="xiaoxiaorenzhe";  
      
    //˽Կ  
    private static final String PRIVATE_KEY="dadapangzi";  
    
    /** 
     * ��ʼ����Կ�� 
     * @return Map �׷���Կ��Map 
     * */  
    public static Map<String,Object> initKey() throws Exception{  
        //ʵ������Կ������  
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance(KEY_ALGORITHM);  
        //��ʼ����Կ������  
        keyPairGenerator.initialize(KEY_SIZE);  
        //������Կ��  
        KeyPair keyPair=keyPairGenerator.generateKeyPair(); 
        //�׷���Կ  
        RSAPublicKey publicKey=(RSAPublicKey) keyPair.getPublic();  
        System.out.println("ϵ����"+publicKey.getModulus()+"  ����ָ����"+publicKey.getPublicExponent());
        //�׷�˽Կ  
        RSAPrivateKey privateKey=(RSAPrivateKey) keyPair.getPrivate(); 
        System.out.println("ϵ����"+privateKey.getModulus()+"����ָ����"+privateKey.getPrivateExponent());
        //����Կ�洢��map��  
        Map<String,Object> keyMap=new HashMap<String,Object>();  
        keyMap.put(PUBLIC_KEY, publicKey);  
        keyMap.put(PRIVATE_KEY, privateKey);  
        return keyMap;  
          
    }  
    
    /** 
     * ˽Կ���� 
     * @param data���������� 
     * @param key ��Կ 
     * @return byte[] �������� 
     * */  
    public static byte[] encryptByPrivateKey(byte[] data,byte[] key) throws Exception{  
          
        //ȡ��˽Կ  
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //����˽Կ  
        PrivateKey privateKey=keyFactory.generatePrivate(pkcs8KeySpec);  
        //���ݼ���  
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);  
        return cipher.doFinal(data);  
    }  
    
    /** 
     * ��Կ���� 
     * @param data���������� 
     * @param key ��Կ 
     * @return byte[] �������� 
     * */  
    public static byte[] encryptByPublicKey(byte[] data,byte[] key) throws Exception{  
          
        //ʵ������Կ����  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //��ʼ����Կ  
        //��Կ����ת��  
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);  
        //������Կ  
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);  
          
        //���ݼ���  
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);  
        return cipher.doFinal(data);  
    }  
    /** 
     * ˽Կ���� 
     * @param data ���������� 
     * @param key ��Կ 
     * @return byte[] �������� 
     * */  
    public static byte[] decryptByPrivateKey(byte[] data,byte[] key) throws Exception{  
        //ȡ��˽Կ  
        PKCS8EncodedKeySpec pkcs8KeySpec=new PKCS8EncodedKeySpec(key);  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //����˽Կ  
        PrivateKey privateKey=keyFactory.generatePrivate(pkcs8KeySpec);  
        //���ݽ���  
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        return cipher.doFinal(data);  
    }  
    
    /** 
     * ��Կ���� 
     * @param data ���������� 
     * @param key ��Կ 
     * @return byte[] �������� 
     * */  
    public static byte[] decryptByPublicKey(byte[] data,byte[] key) throws Exception{  
          
        //ʵ������Կ����  
        KeyFactory keyFactory=KeyFactory.getInstance(KEY_ALGORITHM);  
        //��ʼ����Կ  
        //��Կ����ת��  
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);  
        //������Կ  
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);  
        //���ݽ���  
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());  
        cipher.init(Cipher.DECRYPT_MODE, pubKey);  
        return cipher.doFinal(data);  
    }  
    
    /** 
     * ȡ��˽Կ 
     * @param keyMap ��Կmap 
     * @return byte[] ˽Կ 
     * */  
    public static byte[] getPrivateKey(Map<String,Object> keyMap){  
        Key key=(Key)keyMap.get(PRIVATE_KEY);  
        return key.getEncoded();  
    }  
    /** 
     * ȡ�ù�Կ 
     * @param keyMap ��Կmap 
     * @return byte[] ��Կ 
     * */  
    public static byte[] getPublicKey(Map<String,Object> keyMap) throws Exception{  
        Key key=(Key) keyMap.get(PUBLIC_KEY); 
        return key.getEncoded();  
    }  
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
        //��ʼ����Կ  
        //������Կ��  
        Map<String,Object> keyMap=RSACoder.initKey();  
        //��Կ  
        byte[] publicKey=RSACoder.getPublicKey(keyMap);
        //byte[] publicKey = b;
        //˽Կ  
        byte[] privateKey=RSACoder.getPrivateKey(keyMap);  
        System.out.println("��Կ��"+Base64.encode(publicKey));       
        System.out.println("˽Կ��"+Base64.encode(privateKey));  
          
        System.out.println("================��Կ�Թ������,�׷�����Կ�������ҷ�����ʼ���м������ݵĴ���=============");  
        String str="aattaggcctegthththfef/aat.mp4";  
        System.out.println("===========�׷����ҷ����ͼ�������==============");  
        System.out.println("ԭ��:"+str);  
        //�׷��������ݵļ���  
        byte[] code1=RSACoder.encryptByPublicKey(str.getBytes(), publicKey);
        System.out.println("�׷� ʹ���ҷ���Կ���ܺ�����ݣ�"+Base64.encode(code1));  
        System.out.println("===========�ҷ�ʹ�ü׷��ṩ�Ĺ�Կ�����ݽ��н���==============");  
        //�ҷ��������ݵĽ���  
        //byte[] decode1=RSACoder.decryptByPublicKey(code1, publicKey);  
        byte[] decode1=RSACoder.decryptByPrivateKey(code1, privateKey);  
        System.out.println("�ҷ����ܺ�����ݣ�"+new String(decode1)+"");  
          
        System.out.println("===========������в������ҷ���׷���������==============");  
          
        str="�ҷ���׷���������RSA�㷨";  
          
        System.out.println("ԭ��:"+str);  
          
        //�ҷ�ʹ�ù�Կ�����ݽ��м���  
        byte[] code2=RSACoder.encryptByPublicKey(str.getBytes(), publicKey);  
        System.out.println("===========�ҷ�ʹ�ù�Կ�����ݽ��м���==============");  
        System.out.println("���ܺ�����ݣ�"+Base64.encode(code2));  
          
        System.out.println("=============�ҷ������ݴ��͸��׷�======================");  
        System.out.println("===========�׷�ʹ��˽Կ�����ݽ��н���==============");  
          
        //�׷�ʹ��˽Կ�����ݽ��н���  
        byte[] decode2=RSACoder.decryptByPrivateKey(code2, privateKey);  
          
        System.out.println("�׷����ܺ�����ݣ�"+new String(decode2)); 
        
        
    }  
}