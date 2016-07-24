package com.dohko.core.services;


import com.dohko.core.base.DataMap;
import com.dohko.core.base.Holder;
import com.dohko.core.services.support.BaseClient;
import com.dohko.core.services.support.ContextHolder;

public interface Client {

	DataMap execute(String service, DataMap dataMap, Holder holder);

	void execute(String service, DataMap dataMap, Holder holder, Listener listener);
	
	class Builder {

		public static DataMap execute(String service, DataMap dataMap) {
			Client client = BaseClient.Builder.getClient();
			return client.execute(service, dataMap, ContextHolder.getHolder());
		}

		public static DataMap execute(String service, DataMap dataMap, Listener listener) {
			Client client = BaseClient.Builder.getClient();
			return client.execute(service, dataMap, ContextHolder.getHolder());
		}

		public static DataMap execute(String service, DataMap dataMap, Holder holder) {
			Client client = BaseClient.Builder.getClient();
			return client.execute(service, dataMap, holder);
		}

		public static DataMap execute(String service, DataMap dataMap, Holder holder, Listener listener) {
			Client client = BaseClient.Builder.getClient();
			return client.execute(service, dataMap, holder);
		}

	}

	interface Listener {
		void onCompleted(DataMap dataMap);
	}
}
