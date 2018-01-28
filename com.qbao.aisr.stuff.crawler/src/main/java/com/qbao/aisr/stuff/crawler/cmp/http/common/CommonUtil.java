package com.qbao.aisr.stuff.crawler.cmp.http.common;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


public class CommonUtil {
	public static void downloadImage(String url, String category,String name) {
		final HttpClient httpClient = HttpClientBuilder.create().build();

			try {
				final HttpGet httpGet = new HttpGet(url);
				httpGet.setHeader("User-Agent",
						"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
				// 请求http
				final HttpResponse response = httpClient.execute(httpGet);
				if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
					System.err.println("Method failed: " + response.getStatusLine());
				}
				// save img
				String picName = "img/" + category;
				final File f = new File(picName);
				f.mkdirs();
				picName += "/" + name + ".jpg";
				final InputStream inputStream = response.getEntity().getContent();
				final OutputStream outStream = new FileOutputStream(picName);
				IOUtils.copy(inputStream, outStream);
				outStream.close();
				System.out.println(picName + " OK!");
				httpGet.releaseConnection();
			} catch (final Exception e) {
				e.printStackTrace();
			}
	}

	public static int isWhite(int colorInt, int whiteThreshold) {
		final Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > whiteThreshold) {
			return 1;
		}
		return 0;
	}

	public static int isBlack(int colorInt, int whiteThreshold) {
		final Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= whiteThreshold) {
			return 1;
		}
		return 0;
	}

	public static BufferedImage removeBlank(BufferedImage img, int whiteThreshold, int white) throws Exception {
		final int width = img.getWidth();
		final int height = img.getHeight();
		int start = 0;
		int end = 0;
		Label1: for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				if (isWhite(img.getRGB(x, y), whiteThreshold) == white) {
					start = y;
					break Label1;
				}
			}
		}
		Label2: for (int y = height - 1; y >= 0; --y) {
			for (int x = 0; x < width; ++x) {
				if (isWhite(img.getRGB(x, y), whiteThreshold) == white) {
					end = y;
					break Label2;
				}
			}
		}
		return img.getSubimage(0, start, width, end - start + 1);
	}

	public static BufferedImage removeBackgroud(String picFile, int whiteThreshold) throws Exception {
		final BufferedImage img = ImageIO.read(new File(picFile));
		final int width = img.getWidth();
		final int height = img.getHeight();
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (isWhite(img.getRGB(x, y), whiteThreshold) == 1) {
					img.setRGB(x, y, Color.WHITE.getRGB());
				} else {
					img.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		return img;
	}

	public static Map<BufferedImage, String> loadTrainData(String category) throws Exception {
		final Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
		final File dir = new File("train/" + category);
		final File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".jpg");
			}
		});
		for (final File file : files) {
			map.put(ImageIO.read(file), file.getName().charAt(0) + "");
		}
		return map;
	}

	

	

}
