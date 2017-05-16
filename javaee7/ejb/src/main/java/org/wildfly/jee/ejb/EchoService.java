package org.wildfly.jee.ejb;

import javax.ejb.Remote;

/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Igor</a>
 */
@Remote
public interface EchoService {
    String echo(String message);
}
