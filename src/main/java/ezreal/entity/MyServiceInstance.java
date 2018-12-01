package ezreal.entity;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.cloud.client.ServiceInstance;

public class MyServiceInstance implements ServiceInstance{
	private String serviceId;

	private String host;

	private int port;

	private boolean secure;

	private Map<String, String> metadata;

	public MyServiceInstance() {
		
	}
	public MyServiceInstance(String serviceId, String host, int port, boolean secure,
			Map<String, String> metadata) {
		this.serviceId = serviceId;
		this.host = host;
		this.port = port;
		this.secure = secure;
		this.metadata = metadata;
	}

	public MyServiceInstance(String serviceId, String host, int port,
			boolean secure) {
		this(serviceId, host, port, secure, new LinkedHashMap<String, String>());
	}

	@Override
	public URI getUri() {
		return getUri(this);
	}

	@Override
	public Map<String, String> getMetadata() {
		return this.metadata;
	}

	/**
	 * Create a uri from the given ServiceInstance's host:port
	 * @param instance
	 * @return URI of the form (secure)?https:http + "host:port"
	 */
	public static URI getUri(ServiceInstance instance) {
		String scheme = (instance.isSecure()) ? "https" : "http";
		String uri = String.format("%s://%s:%s", scheme, instance.getHost(),
				instance.getPort());
		return URI.create(uri);
	}

	@Override
	public String getServiceId() {
		return serviceId;
	}

	@Override
	public String getHost() {
		return host;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public boolean isSecure() {
		return secure;
	}

	@Override
	public String toString() {
		return "MyServiceInstance{" +
				"serviceId='" + serviceId + '\'' +
				", host='" + host + '\'' +
				", port=" + port +
				", secure=" + secure +
				", metadata=" + metadata +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MyServiceInstance that = (MyServiceInstance) o;
		return port == that.port &&
				secure == that.secure &&
				Objects.equals(serviceId, that.serviceId) &&
				Objects.equals(host, that.host) &&
				Objects.equals(metadata, that.metadata);
	}

	@Override
	public int hashCode() {
		return Objects.hash(serviceId, host, port, secure, metadata);
	}
}
