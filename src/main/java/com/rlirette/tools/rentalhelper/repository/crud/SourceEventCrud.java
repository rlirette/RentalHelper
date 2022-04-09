package com.rlirette.tools.rentalhelper.repository.crud;

import com.rlirette.tools.rentalhelper.model.dao.Source;
import com.rlirette.tools.rentalhelper.model.dao.ics.SourceIcs;
import com.rlirette.tools.rentalhelper.repository.SourceEventRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SourceEventCrud {

    private final SourceEventRepository sourceEventRepository;

    public Source initNew(Source source){
        return sourceEventRepository.save(source);
    }

    public Source findBy(String name){
        return sourceEventRepository.findByNameIgnoreCase(name).orElseThrow();
    }

    public TemplateSelectors findAll(){
        final List<Source> sources = sourceEventRepository.findAll();
        return new TemplateSelectors(sources);
    }

    public TemplateSelector findByName(String name){
        final Source source = sourceEventRepository.findByNameIgnoreCase(name).orElseThrow();
        return new TemplateSelector(source);
    }

    public Set<String> deductIcsCalendarUriFrom(Set<SourceIcs> sourceIcs){
        return sourceIcs.stream()
                .map(SourceIcs::getIcsCalendarUri)
                .collect(Collectors.toSet());
    }

    public void update(Source source){
        sourceEventRepository.save(source);
    }

    @AllArgsConstructor
    public class TemplateSelector{

        private Source source;

        public Source withTemplateName(String templateName){
            source.getSourceMail().getSourceMailContentTemplate().removeIf(t -> !t.getTemplateName().equals(templateName));
            return source;
        }
    }

    @AllArgsConstructor
    public class TemplateSelectors{

        private List<Source> sources;

        public List<Source> withTemplateName(String templateName){
            sources.forEach(source -> source.getSourceMail().getSourceMailContentTemplate().removeIf(t -> !t.getTemplateName().equals(templateName)));
            return sources;
        }
    }
}
