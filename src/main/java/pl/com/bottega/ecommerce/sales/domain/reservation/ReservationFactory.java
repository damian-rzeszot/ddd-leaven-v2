/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.reservation;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import pl.com.bottega.ddd.annotations.domain.DomainFactory;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.AggregateId;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation.ReservationStatus;
import pl.com.bottega.ecommerce.sharedkernel.exceptions.DomainOperationException;

@DomainFactory
public class ReservationFactory {

	@Inject
	private AutowireCapableBeanFactory spring;
	
	public Reservation create(Client client){
		if (! canReserve(client))
			throw new DomainOperationException(client.getAggregateId(), "Client can not create reservations");
		
		Reservation reservation = new Reservation(AggregateId.generate(), ReservationStatus.OPENED, client.generateSnapshot(), new Date());
		spring.autowireBean(reservation);
		
		addGratis(reservation, client);
		
		return reservation;
	}

	private void addGratis(Reservation reservation, Client client) {				
		//TODO explore domain rules
	}

	private boolean canReserve(Client client) {
		return true;//TODO explore domain rules (ex: client's debts, statuses etc) 
	}

}
