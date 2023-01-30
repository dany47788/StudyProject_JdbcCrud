package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.PostDto;
import org.example.dto.mapper.LabelDtoMapper;
import org.example.dto.mapper.PostDtoMapper;
import org.example.dto.mapper.PostMapper;
import org.example.repository.impl.LabelRepositoryImpl;
import org.example.repository.impl.PostRepositoryImpl;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepositoryImpl postRepositoryImpl;
    private final LabelRepositoryImpl labelRepositoryImpl;
    private final LabelDtoMapper labelDtoMapper;
    private final PostDtoMapper postDtoMapper;
    private final PostMapper postMapper;

    public List<PostDto> findAll() {

        return postRepositoryImpl.findAll().stream()
            .map(postDtoMapper::map)
            .toList();
    }

    public PostDto findById(Integer id) {

        if (id == null) {

            return null;
        }

        var postDto = postDtoMapper.map(postRepositoryImpl.findById(id));

        postDto.setLabels(labelRepositoryImpl.findByPostId(id).stream()
            .map(labelDtoMapper::map)
            .toList());

        return postDto;
    }

    public PostDto create(PostDto postDto) {

        if (postDto == null) {

            return null;
        }

        postRepositoryImpl.create(postMapper.map(postDto));

        log.info("{} - created.", postDto);

        return postDto;
    }

    public PostDto update(PostDto postDto) {

        if (postDto == null) {

            return null;
        }

        postRepositoryImpl.update(postMapper.map(postDto));

        log.info("Post with id = {} - updated.", postDto.getId());

        return postDto;
    }

    public void deleteById(Integer id) {

        postRepositoryImpl.deleteById(id);

        log.info("Post with id = {} - deleted.", id);
    }
}
