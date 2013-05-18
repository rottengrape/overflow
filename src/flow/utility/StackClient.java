package flow.utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import flow.entry.Question;
import flow.entry.Site;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONException;

import android.util.Log;

public class StackClient {

	private final String host = "api.stackexchange.com";
	private final int soTimeout = 7000;
    private final int timeout = 17000;
	private final String agent = "flow";
	private final String questionFilter = "!)(eSXacekjPWNDpf7tU2)reefp2-2Rc2f97hgcYjQqEQA3X0Ykawy";

	private static StackClient instance;


	private DefaultHttpClient client;
	private BasicHttpParams params;
	private ThreadSafeClientConnManager manager;

	private StackClient() {

		params = new BasicHttpParams();
		SchemeRegistry registry = new SchemeRegistry();
		Scheme http = new Scheme("http", PlainSocketFactory.getSocketFactory(),
				80);
		registry.register(http);

		HttpClientParams.setRedirecting(params, true);
		HttpConnectionParams.setConnectionTimeout(params, timeout);
		HttpConnectionParams.setSoTimeout(params, timeout);

		HttpProtocolParams.setUserAgent(params, agent);

		manager = new ThreadSafeClientConnManager(params, registry);
		client = new DefaultHttpClient(manager, params);

	}

	public synchronized static StackClient getInstance() {

		if (instance == null) {
			instance = new StackClient();
		}
		return instance;

	}

	private String buildPath(String site, String filter, String prefixPath,
			int page, int pageSize, long[] id) {

		StringBuilder builder = new StringBuilder();
		builder.append("http://").append(host);
		builder.append("/2.0/").append(prefixPath);

		if (id != null && id.length > 0) {

			builder.append("/");

			for (long i : id) {

				builder.append(i).append(";");

			}
			builder.deleteCharAt(builder.length() - 1);
		}
		builder.append("?");

		if (page > 0 && pageSize > 0) {

			builder.append("page=").append(page).append("&pagesize=")
					.append(pageSize).append("&");

		}

		builder.append("order=desc").append("&sort=activity");
		builder.append("&site=").append(site);

		if (filter != null) {

			builder.append("&filter=").append(filter);

		}

		return builder.toString();

	}
	
	public Site[] sites() throws JSONException {
		
		String path = "http://api.stackexchange.com/sites?page=1&pagesize=177";
		String json = doGet(path);

		return Site.parseJson(json);
		
		
		
		
	}
	
	public Question[] questions(String site,int page, long... id) throws JSONException {

		String path = buildPath(site, null, "questions", page, 17, id);
		String json = doGet(path);
		return Question.parseJson(json);

	}

	public Question[] questionsAnswersComments(String site, long... id)
			throws JSONException {

		String path = buildPath(site, questionFilter, "questions", 0, 0, id);
		String json = doGet(path);
		
		Question[] q = Question.parseJson(json);

		return q;

	}
	
	
	public String doGet(String path) {

		String getResponse = null;

		HttpGet get = new HttpGet(path);
		GZIPInputStream input = null;
		ByteArrayOutputStream output = null;

		try {

			byte[] buffer = new byte[1024];

			HttpResponse response = client.execute(get);

			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();

			input = new GZIPInputStream(new BufferedInputStream(content));

			output = new ByteArrayOutputStream();

			int flag = 0;

			while ((flag = input.read(buffer)) != -1) {

				output.write(buffer, 0, flag);

			}

			getResponse = output.toString();

		} catch (ClientProtocolException e) {


		} catch (IOException e) {

			if (input != null) {
				try {
					input.close();
				} catch (IOException e1) {

				}
			}

			if (output != null) {
				try {

					output.close();

				} catch (IOException e1) {

				}
			}

		}

		return getResponse;

	}

}
