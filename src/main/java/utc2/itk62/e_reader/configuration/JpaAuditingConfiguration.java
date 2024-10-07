package utc2.itk62.e_reader.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import utc2.itk62.e_reader.audit.AuditorAwareImpl;

@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
    @Bean
    public AuditorAwareImpl auditorAware(){
        return new AuditorAwareImpl();
    }
}
