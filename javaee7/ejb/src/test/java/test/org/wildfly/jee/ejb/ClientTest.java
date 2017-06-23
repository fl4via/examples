package test.org.wildfly.jee.ejb;

import java.security.Security;
import java.util.Properties;

import javax.naming.InitialContext;

import org.jboss.ejb.client.ClusterAffinity;
import org.jboss.ejb.client.EJBClient;
import org.jboss.ejb.client.NodeAffinity;
import org.junit.Test;
import org.wildfly.jee.ejb.EchoService;
import org.wildfly.security.WildFlyElytronProvider;

/**
 * @author <a href="mailto:psilva@redhat.com">Pedro Igor</a>
 */
public class ClientTest {

    static {
        Security.addProvider(new WildFlyElytronProvider());
    }
    @Test
    public void testInvocation() throws Exception {
        Properties p = new Properties();

        p.put("java.naming.factory.url.pkgs","org.jboss.ejb.client.naming");
        //p.put("jboss.naming.client.ejb.context", "true");

        InitialContext context = new InitialContext(p);

        Object bean = context.lookup("ejb:/ejb/EchoServiceBean!org.wildfly.jee.ejb.EchoService?stateful");
        EchoService service = EchoService.class.cast(bean);

        System.out.println("Proxy before setting strong affinity: " + service.toString());
        // EJBClient.setStrongAffinity(service, Affinity.NONE);
        // EJBClient.setStrongAffinity(service, new NodeAffinity("node1"));
        EJBClient.setStrongAffinity(service, new ClusterAffinity("ejb"));
        System.out.println("Proxy after setting strong affinty: " + service.toString());

        for (int i = 0; i < 50; i++){
            invoke(service);
            System.out.println("About to sleep ...");
            Thread.sleep(5000);
        }
    }

    private void invoke(EchoService service) {
        for (int i = 0; i < 1; i++) {
            String hello = service.echo("Hello");
            System.out.println(hello);
        }
    }
}