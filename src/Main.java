import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Main {
    
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        
       Scanner 
       scanner = new Scanner(System.in);
       Random random = new Random();
 
       int n;
       for (; ;) {
        System.out.println("");  
        System.out.println("Enter the number of possible moves");
        System.out.println("");     
        n = scanner.nextInt();
        if (n%2 == 0 | n<3 ) {
            System.out.println(""); 
            System.out.println("The number of possible moves cannot be less than 3 and must be odd!!! FOR EXAMPLE: Rock Paper Scissors or Rock Paper Scissors Lizard Spock or 1 2 3 4 5 6 7 8 9 10 11");
            System.out.println("");
           } else {
                break;
            };

       };

       String[] plays = new String[n];
       for (int i=0; i<plays.length; i++){
            System.out.println("");
            System.out.println("Enter the " + (i+1) +"st option of the move");
            plays[i] = scanner.next();    
        };
       
       for (; ;) {
            SecureRandom rnd = new SecureRandom();
            byte[] rndData = new byte[16]; 
            rnd.nextBytes(rndData);
            StringBuilder sb = new StringBuilder();
            for (byte b : rndData) {
                sb.append(String.format("%02x", b));
            };

            String SecKey = sb.toString();
            String compMove;
            int index = random.nextInt(plays.length);
            compMove = plays[index];
    
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256"); 
            sha256_HMAC.init(new SecretKeySpec( SecKey.getBytes(), "HmacSHA256"));
            byte [] result = sha256_HMAC.doFinal(compMove.getBytes());
        
            StringBuilder s = new StringBuilder();
            for (byte b : result) {
                s.append(String.format("%02x", b));
            };

            System.out.println("");
            System.out.println("HMAC: " + s );
            System.out.println("");
            System.out.println("Available moves:");
            System.out.println("");
            for (int i=0; i<plays.length; i++) {
                System.out.println((i+1)+" - " + plays[i] );
                System.out.println("");
            };
            System.out.println("0 - exit" );
            System.out.println("");
            
            int userMove;
            System.out.println("Enter your move:");
            userMove = scanner.nextInt();
            if (userMove == 0 ) {
                return;
            } 
            else if (userMove>plays.length) { 
                System.out.println("");
                System.out.println("Available moves:");
                System.out.println("");
                for (int i=0; i<plays.length; i++) {
                    System.out.println((i+1)+" - " + plays[i] );
                    System.out.println("");
            }
            } else {
                System.out.println("");
                System.out.println("Your move: "+ plays[userMove-1]);
                System.out.println("");
                
                System.out.println("Computer move: " +  compMove);
                System.out.println("");

                int half = (plays.length-1)/2;
                int r=0;
                if ((userMove-1)==index) {
                    System.out.println("DRAW!!!");

                } else if ((userMove-1) <= half) {
                    for (int j=1;j<=half;j++) {
                        if (((userMove-1)+j) == index) {
                            r=1;
                        }
                    };
                    if (r!=0) {
                        System.out.println("Computer WIN!!!!");
                    } else {
                        System.out.println("You WIN!!!!");
                    }
                } else if ((userMove-1) >= half) {
                    for (int j=1;j<=half;j++) {
                        if (((userMove-1)-j) == index) {
                           r=1;
                        }
                       
                    };
                    if (r!=0) {
                        System.out.println("You WIN!!!!");
                       
                    } else {
                        System.out.println("Computer WIN!!!!");
                    }; 
                }; 

                System.out.println("");
                System.out.println("HMAC key: " + SecKey);
            };

        }
    }; 
    };


