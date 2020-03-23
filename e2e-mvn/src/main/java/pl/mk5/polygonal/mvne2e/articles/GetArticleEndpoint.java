package pl.mk5.polygonal.mvne2e.articles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import pl.mk5.polygonal.mvne2e.articles.dto.Article;
import reactor.core.publisher.Mono;

@RestController
class GetArticleEndpoint {

    @GetMapping("/article/{id}")
    public Mono<Article> article(@PathVariable UUID id) {
        return Mono.just(new Article(id));
    }
}
