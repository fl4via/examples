package org.wildfly.jee.ejb;

import javax.ejb.Remote;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.Clustered;

/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Igor</a>
 */
@Stateful
@Remote(EchoService.class)
@Clustered
public class EchoServiceBean implements EchoService {
    public String echo(String message) {
        return "Echo from " + System.getProperty("jboss.node.name") + ": \"" + message + "\"";
    }
}
