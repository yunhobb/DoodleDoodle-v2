package com.doodledoodle.backend.dictionary.service;

import com.doodledoodle.backend.dictionary.dto.response.DictionaryResponseDto;
import com.doodledoodle.backend.dictionary.entity.Dictionary;
import com.doodledoodle.backend.dictionary.entity.StorageType;
import com.doodledoodle.backend.dictionary.mapper.DictionaryMapper;
import com.doodledoodle.backend.dictionary.repository.DictionaryRepository;
import com.doodledoodle.backend.global.exception.EntityNotFoundException;
import com.doodledoodle.backend.global.storage.LocalStorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Primary
@Service
@RequiredArgsConstructor
public class DictionaryLocalServiceImpl implements DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final DictionaryMapper dictionaryMapper;
    private final LocalStorageProperties localStorageProperties;

    @Override
    public void initializeDictionary() {
        try (
                BufferedReader engReader = new BufferedReader(new FileReader(localStorageProperties.getLocation() + "/engclasses.txt"));
                BufferedReader koReader = new BufferedReader(new FileReader(localStorageProperties.getLocation() + "/classes.txt"))) {

            String koLine;
            String engLine;
            List<String> koWords = new ArrayList<>();
            List<String> engWords = new ArrayList<>();
            List<Dictionary> dictionaries = new ArrayList<>();

            while (StringUtils.hasText(engLine = engReader.readLine()) && StringUtils.hasText(koLine = koReader.readLine())) {
                engWords.add(engLine);
                koWords.add(koLine);
            }

            for (int i = 0; i < koWords.size(); i++) {
                String engWord = engWords.get(i);
                String koWord = koWords.get(i);
                dictionaries.add(dictionaryMapper.toEntity(koWord, engWord, getImageUrl(engWord), StorageType.LOCAL));
            }

            dictionaryRepository.saveAll(dictionaries);
        } catch (IOException e) {}
    }

    @Override
    public DictionaryResponseDto getRandomDictionary() {
        long randomNum = (long) (Math.random() * 99) + 1;
        return dictionaryMapper.toResponse(getEntity(randomNum));
    }

    private String getImageUrl(String engWord) {
        return localStorageProperties.getLocation() + "/dictionary/" + engWord + ".png";
    }

    private Dictionary getEntity(Long id) {
        return dictionaryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
