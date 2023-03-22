package ru.vaszol.yobit_2023.api;

import com.google.gson.stream.JsonReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.vaszol.yobit_2023.model.Rate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.*;

@Service
public class YoBitAPI {
    private static final String API_URL = "https://yobit.net/tapi/";
    private static final String HASH_ALGORITHM = "HmacSHA512";
    @Value("${my.property.Sign}")
    private static String Sign;
    @Value("${my.property.Key}")
    private static String Key;

    private static JsonReader postRequest(String method, Map<String, String> data) {
        try {
            URL obj = new URL(API_URL);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            StringBuilder postdata = new StringBuilder("method=").append(method).append('&').append("nonce=").append(new Date().getTime());
            for (Map.Entry<String, String> entry : data.entrySet())
                postdata.append('&').append(entry.getKey()).append('=').append(entry.getValue());

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; JAVA AWT)");
            con.setRequestProperty("Sign", Sign);
            con.setRequestProperty("Key", Key);
            con.setUseCaches(false);
            con.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postdata.toString());
            wr.flush();
            wr.close();
            System.out.println("con = " + con.getURL());
            System.out.println("con = " + con.getRequestProperties());

            return new JsonReader(new InputStreamReader(con.getInputStream()));

        } catch (Exception e) {
        }
        return null;
    }

    public static JsonReader getRequest(String url) {
        try {
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; JAVA AWT)");
            con.setUseCaches(false);
            con.connect();

            return new JsonReader(new InputStreamReader(con.getInputStream()));

        } catch (Exception e) {
        }
        return null;
    }

    private static String encode(String key, String data) throws Exception {
        try {
            Mac sha256_HMAC = Mac.getInstance(HASH_ALGORITHM);

            sha256_HMAC.init(new SecretKeySpec(key.getBytes("UTF-8"), HASH_ALGORITHM));

            return toHexString(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
        } catch (InvalidKeyException e) {
            throw new SignatureException("Error building signature, invalid key " + HASH_ALGORITHM);
        }

    }

    private static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);

        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        String r = sb.toString();

        formatter.close();

        return r;

    }

    static String decimal(double d) {
        return String.format(Locale.US, "%.8f", d);
    }

    public static double rate;
    public static HashMap<String, Double> funds;
    public static HashMap<String, Double[]> sellmap;
    public static HashMap<String, ArrayList<String>> sellorders;
    public static HashMap<String, Double> totalfunds;

    /**
     * запрос актуального спроса пары
     * @param pair - пара, например "usd_rur"
     * @return спрос
     */
    public Rate getRate(String pair) throws IOException {
        JsonReader reader = getRequest("https://yobit.net/api/3/depth/" + pair + "?limit=2");

        Rate rateEntity = new Rate();
        rateEntity.setPair(pair);
        rateEntity.setDate(new Date());

        reader.beginObject();

        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("error")) {
                System.out.println("reader.nextString() = " + reader.nextString());
//				Main.doStop(reader.nextString());

                while (reader.hasNext()) {
                    reader.nextName();
                    reader.skipValue();
                }
            } else if (name.equals(pair)) {
                reader.beginObject();
                while (reader.hasNext()) {
                    name = reader.nextName();
                    if (name.equals("asks")) {
                        reader.beginArray();
                        reader.beginArray();
                        double r = reader.nextDouble();
                        if (r != rate) {
                            rate = r;
                            rateEntity.setAsk(decimal(rate));
//							Main.doOutput("Rate is now " + decimal(r));
//							Main.rateLabel.setText("Rate: " + decimal(rate));
                        }
                        while (reader.hasNext()) {
                            reader.skipValue();
                        }
                        reader.endArray();
                        while (reader.hasNext()) {
                            reader.skipValue();
                        }
                        reader.endArray();
                    } else if (name.equals("bids")) {
                        reader.beginArray();
                        reader.beginArray();
                        double r = reader.nextDouble();
                        if (r != rate) {
                            rate = r;
                            rateEntity.setBid(decimal(rate));
//							Main.doOutput("Rate is now " + decimal(r));
//							Main.rateLabel.setText("Rate: " + decimal(rate));
                        }
                        while (reader.hasNext()) {
                            reader.skipValue();
                        }
                        reader.endArray();
                        while (reader.hasNext()) {
                            reader.skipValue();
                        }
                        reader.endArray();
                    } else {
                        reader.skipValue();
                    }
                }
                reader.endObject();
            } else {
                reader.skipValue();
            }
        }

        reader.endObject();
        reader.close();
        return rateEntity;
    }

//	public static void getFunds() throws IOException {
//		funds = new HashMap<String, Double>();
//
//		JsonReader reader = postRequest("getInfo", new HashMap<String, String>());
//
//		reader.beginObject();
//
//		while (reader.hasNext()) {
//			String name = reader.nextName();
//
//			if (name.equals("error")) {
//				System.out.println(reader.nextString());
////				Main.doStop(reader.nextString());
//
//				while (reader.hasNext()) {
//					reader.nextName();
//					reader.skipValue();
//				}
//			} else if (name.equals("return")) {
//
//				reader.beginObject();
//
//				while (reader.hasNext()) {
//					name = reader.nextName();
//
//					if (name.equals("funds")) {
//
//						reader.beginObject();
//
//						while (reader.hasNext()) {
//							String symbol = reader.nextName();
//							double value = reader.nextDouble();
//							if (value > 0d) funds.put(symbol, value);
//						}
//
//						reader.endObject();
//
//					} else {
//						reader.skipValue();
//					}
//
//				}
//
//				reader.endObject();
//
//			} else {
//
//				reader.skipValue();
//
//			}
//
//		}
//
//		reader.endObject();
//		reader.close();
//
//	}

//	public static void getOrders() throws IOException {
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("pair", Main.textFieldTradingPair.getText());
//		JsonReader reader = postRequest("ActiveOrders", map);
//
//		totalfunds = (HashMap<String, Double>) funds.clone();
//
//		sellmap = new HashMap<String, Double[]>();
//		sellorders = new HashMap<String, ArrayList<String>>();
//
//		reader.beginObject();
//
//		while (reader.hasNext()) {
//			String name = reader.nextName();
//
//			if (name.equals("error")) {
//				Main.doStop(reader.nextString());
//
//				while (reader.hasNext()) {
//					reader.nextName();
//					reader.skipValue();
//				}
//			} else if (name.equals("return")) {
//				/* Key is valid, save */
//				Settings.put("key.txt", Main.textFieldAPIKey.getText());
//				Settings.put("secret.txt", Main.textFieldAPISecret.getText());
//
//				reader.beginObject();
//
//				while (reader.hasNext()) {
//					String order_id = reader.nextName();
//
//					reader.beginObject();
//
//					while (reader.hasNext()) {
//						name = reader.nextName();
//
//						if (name.equals("type")) {
//							/* Sell */
//							if (reader.nextString().equals("sell")) {
//								double rate = 0;
//								double amount = 0;
//
//								while (reader.hasNext()) {
//									name = reader.nextName();
//
//									if (name.equals("rate")) {
//										rate = reader.nextDouble();
//									} else if (name.equals("amount")) {
//										amount = reader.nextDouble();
//									} else {
//										reader.skipValue();
//									}
//								}
//
//								double f = funds.get(Main.textFieldTradingPair.getText().split("_")[0].toLowerCase());
//								funds.put(Main.textFieldTradingPair.getText().split("_")[0].toLowerCase(), Math.max(0, f - amount));
//
//								totalfunds.put("btc", totalfunds.get("btc") + (amount * rate));
//
//								ArrayList<String> l;
//								if (sellmap.containsKey(decimal(rate))) {
//									Double[] m = sellmap.get(decimal(rate));
//									m[0]++;
//									m[1] += amount;
//									sellmap.put(decimal(rate), m);
//
//									l = sellorders.get(decimal(rate));
//									l.add(order_id);
//								} else {
//									sellmap.put(decimal(rate), new Double[]{1d, amount});
//									l = new ArrayList<String>();
//									l.add(order_id);
//								}
//								sellorders.put(decimal(rate), l);
//
//								continue;
//							}
//
//							/* Buy */
//							double a = 0, r = 0;
//
//							while (reader.hasNext()) {
//								name = reader.nextName();
//								if (name.equals("rate")) {
//									r = reader.nextDouble();
//								} else if (name.equals("amount")) {
//									a = reader.nextDouble();
//								} else {
//									reader.skipValue();
//								}
//							}
//
//							if (!decimal(r).equals(decimal(rate))) {
//								/* Cancel order */
//								map = new HashMap<String, String>();
//								map.put("order_id", order_id);
//								Main.doOutput("Cancelling order " + order_id + ", do not want to buy " + decimal(a) + " at " + decimal(r));
//								JsonReader or = postRequest("CancelOrder", map);
//								or.close();
//							} else {
//								double f = funds.get("btc");
//								funds.put("btc", Math.max(0, f - ((a * r) * 1.002)));
//							}
//						} else {
//							reader.skipValue();
//						}
//
//					}
//
//					reader.endObject();
//
//				}
//
//				reader.endObject();
//			} else {
//				reader.skipValue();
//			}
//
//		}
//
//		reader.endObject();
//		reader.close();
//
//		/*
//		 *    /* Check if our buy orders have resulted in actual purchases, and if so, re-sell those just high enough to make a profit
//		if( $funds[ strtolower( SYMBOL ) ] > 100 )
//		{
//		        // Is the total amount of coins available to sell, big enough to sell?
//		        $sell = ( ( ceil( ( $rate * 1E8 ) / 50 ) * 50 ) + 20 ) / 1E8 - (SATOSHI * 4);
//		        if( $sell * $funds[ strtolower( SYMBOL ) ] > 0.0001 )
//		        {
//		                $trade->apiQuery("Trade",
//		                        array(
//		                                'pair'=>TRADING_PAIR,
//		                                'type'=>'sell',
//		                                'rate'=>decimal( $sell ),
//		                                'amount'=>decimal( $funds[ strtolower( SYMBOL ) ] )
//		                        )
//		                );
//
//		                echo "Selling " . decimal( $funds[ strtolower( SYMBOL ) ] ) . " " . SYMBOL . " at " . decimal( $sell ) . "\n";
//		                echo "==========================================================\n";
//		        }
//		}
//		*/
//
//		ArrayList sortedKeys = new ArrayList(sellmap.keySet());
//		Collections.sort(sortedKeys);
//		Iterator srt = sortedKeys.iterator();
//
//		/* Combine orders, save on fees */
//		while (srt.hasNext()) {
//			String key = (String) srt.next();
//
//			if (sellmap.get(key)[0] > 1) {
//				Main.doOutput("Combining " + Integer.toString((int) sellmap.get(key)[0].doubleValue()) + " sell orders at " + key + " into one sell order of " + decimal(sellmap.get(key)[1]) + " to save transaction fees.");
//				for (String entr : sellorders.get(key)) {
//					/* Cancel order */
//					map = new HashMap<String, String>();
//					map.put("order_id", entr);
//
//					/* Uncomment for final */
//					JsonReader or = postRequest("CancelOrder", map);
//					or.close();
//				}
//
//				map = new HashMap<String, String>();
//				map.put("pair", Main.textFieldTradingPair.getText());
//				map.put("type", "sell");
//				map.put("rate", key);
//				map.put("amount", decimal(sellmap.get(key)[1]));
//
//				JsonReader or = postRequest("Trade", map);
//				or.close();
//			}
//		}
//
//		srt = sortedKeys.iterator();
//		DefaultTableModel model = (DefaultTableModel) Main.tableSell.getModel();
//		model.setRowCount(0);
//
//		while (srt.hasNext()) {
//			String key = (String) srt.next();
//			String[] data = new String[4];
//			data[0] = Main.textFieldTradingPair.getText().split("_")[0].toUpperCase();
//			data[1] = decimal(sellmap.get(key)[1]);
//			data[2] = key;
//			data[3] = decimal(sellmap.get(key)[1] * Double.parseDouble(key));
//			model.addRow(data);
//		}
//
//		sortedKeys = new ArrayList(totalfunds.keySet());
//		Collections.sort(sortedKeys);
//		srt = sortedKeys.iterator();
//
//		model = (DefaultTableModel) Main.tableValue.getModel();
//		model.setRowCount(0);
//
//		while (srt.hasNext()) {
//			String key = (String) srt.next();
//
//			String[] data = new String[3];
//
//			String a = decimal(funds.get(key));
//			String b = decimal(totalfunds.get(key));
//
//			data[0] = key.toUpperCase();
//			data[1] = a;
//			data[2] = a.equals(b) ? "" : b;
//
//			model.addRow(data);
//
//			//MainWindow.doOutput(entry.getKey() + " " + decimal(entry.getValue()) + " " + decimal(funds.get(entry.getKey())));
//		}
//
//		if (funds.containsKey(Main.textFieldTradingPair.getText().split("_")[0].toLowerCase()) && funds.get(Main.textFieldTradingPair.getText().split("_")[0].toLowerCase()) > 100) {
//
//			double sellval = ((Math.ceil((rate * 1E8) / 50) * 50) + 20) / 1E8;
//			if (sellval * funds.get(Main.textFieldTradingPair.getText().split("_")[0].toLowerCase()) > 0.0001) {
//
//				map = new HashMap<String, String>();
//				map.put("pair", Main.textFieldTradingPair.getText());
//				map.put("type", "sell");
//				map.put("rate", decimal(sellval));
//				map.put("amount", decimal(funds.get(Main.textFieldTradingPair.getText().split("_")[0].toLowerCase())));
//
//				JsonReader or = postRequest("Trade", map);
//				or.close();
//
//				Main.doOutput("Selling " + decimal(funds.get(Main.textFieldTradingPair.getText().split("_")[0].toLowerCase())) + " at " + decimal(sellval) + ". Total BTC increased. YAY!!");
//			}
//
//		}
//
//		if (funds.get("btc") > 0.0001 && !sellmap.containsKey(decimal(rate))) {
//			double amount = 0;
//			if (Main.fixed) {
//				try {
//					amount = Double.parseDouble(Main.txtFixedamount.getText().toString());
//				} catch (NumberFormatException e) {
//					Main.fixed = false;
//				}
//			}
//			if (!Main.fixed) {
//				amount = (Math.min(funds.get("btc"), 0.0001) / rate) + ((Math.random() * 5000000) / 5000000);
//			}
//
//			map = new HashMap<String, String>();
//			map.put("pair", Main.textFieldTradingPair.getText());
//			map.put("type", "buy");
//			map.put("rate", decimal(rate));
//			map.put("amount", decimal(amount));
//
//			JsonReader or = postRequest("Trade", map);
//			or.close();
//
//			Main.doOutput("Moving market by buying " + decimal(amount) + " at " + decimal(rate));
//
//		}
//
//	}
}
