package com.yosto.yostobackend.studierichtingWaardes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StudierichtingWaardesService {

    private final StudierichtingWaardesRepository repository;

    public StudierichtingWaardesService(StudierichtingWaardesRepository repository) {
        this.repository = repository;
    }

    public StudierichtingWaardes save(StudierichtingWaardes waardes) {
        return repository.save(waardes);
    }

    public List<StudierichtingWaardes> findAll() {
        return repository.findAll();
    }

    public StudierichtingWaardes findById(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public List<StudierichtingSimilarityDto> calculateSimilarity(UserValuesDto userValues) {
        List<StudierichtingWaardes> allWaardes = findAll();
        return allWaardes.stream()
                .map(waardes -> {
                    int totalDifference = Math.abs(waardes.getConventioneel() - userValues.getConventioneel()) +
                            Math.abs(waardes.getPraktisch() - userValues.getPraktisch()) +
                            Math.abs(waardes.getAnalytisch() - userValues.getAnalytisch()) +
                            Math.abs(waardes.getKunstzinnig() - userValues.getKunstzinnig()) +
                            Math.abs(waardes.getSociaal() - userValues.getSociaal()) +
                            Math.abs(waardes.getOndernemend() - userValues.getOndernemend());
                    int maxDifference = 100 * 6; // 100 is the max value per topic, 6 topics in total
                    double similarityPercentage = ((double) (maxDifference - totalDifference) / maxDifference) * 100;

                    return new StudierichtingSimilarityDto(
                            waardes.getStudierichting().getId(),
                            waardes.getStudierichting().getNaam(),
                            waardes.getStudierichting().getNiveauNaam(),
                            waardes.getConventioneel(),
                            waardes.getPraktisch(),
                            waardes.getAnalytisch(),
                            waardes.getKunstzinnig(),
                            waardes.getSociaal(),
                            waardes.getOndernemend(),
                            similarityPercentage
                    );
                })
                .sorted((dto1, dto2) -> Double.compare(dto2.getSimilarityPercentage(), dto1.getSimilarityPercentage())) // Sort by similarity percentage in descending order
                .collect(Collectors.toList());
    }

    public StudierichtingWaardes findByStudierichtingId(UUID studierichtingId) {
        return repository.findByStudierichtingId(studierichtingId);
    }
}
