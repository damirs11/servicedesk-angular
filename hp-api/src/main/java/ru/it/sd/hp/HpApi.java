package ru.it.sd.hp;

import com.hp.itsm.ssp.beans.SdClientBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import ru.it.sd.model.DynamicAuthentication;
import ru.it.sd.util.ResourceMessages;

/**
 * Содержит базовые операции для работы с HP API
 *
 * @author quadrix
 * @since 28.07.2017
 */
@Repository
public class HpApi {

	public SdClientBean getSdClient() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication != null && authentication instanceof DynamicAuthentication) {
			return ((DynamicAuthentication) authentication).getActualSdClient();
		}
		throw new IllegalStateException(ResourceMessages.getMessage("error.cannot.get.hp.api"));
	}

}
