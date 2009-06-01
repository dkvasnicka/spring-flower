/*
FLOWER framework for Spring -- expose your Spring beans through HTTP -- http://code.google.com/p/spring-flower/
Copyright (C) 2009 Daniel Kvasnička jr.

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
*/

package net.danielkvasnicka.flower.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.xml.BeanDefinitionDecorator;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Node;

/**
 * A def. decorator that contains the logic executed when Spring
 * encounters the "url-mapping" attribute on a bean.
 * It stores the mapping to the mapping holder bean present in the current context.
 * 
 * @author Daniel Kvasnicka jr.
 * @see BeanDefinitionDecorator
 */
public class FlowerURLMappingBeanDefinitionDecorator implements
		BeanDefinitionDecorator {
	
	/**
	 * @see org.springframework.beans.factory.xml.BeanDefinitionDecorator#decorate(org.w3c.dom.Node, org.springframework.beans.factory.config.BeanDefinitionHolder, org.springframework.beans.factory.xml.ParserContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BeanDefinitionHolder decorate(final Node node, BeanDefinitionHolder bdef,
			ParserContext parseCtx) {
		
		final BeanDefinition urlMappingHolderBeanDefinition = parseCtx.getRegistry().getBeanDefinition(Constants.URL_MAPPING_HOLDER_BEAN_NAME);
		final PropertyValue propertyVal = urlMappingHolderBeanDefinition.getPropertyValues().getPropertyValue(Constants.URL_MAPPING_HOLDER_PROPERTY);
		Map<String, String> urlMapping;
		
		if (propertyVal == null) {
			urlMapping = new HashMap<String, String>();
		} else {
			urlMapping = (Map<String, String>) propertyVal.getValue();
		}
		
		urlMapping.put(node.getNodeValue(), bdef.getBeanName());
		urlMappingHolderBeanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("urlMapping", urlMapping));
		
		return bdef;
	}

}
