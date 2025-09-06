package com.study.springboot.service.impl;

import com.study.springboot.domain.Item;
import com.study.springboot.repository.ItemRepository;
import com.study.springboot.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    @Override
    public void register(Item item) throws Exception {      

        log.debug("ItemServiceImpl.register");

        repository.save(item);
    }

    @Override
    public Item read(Long itemId) throws Exception {

        log.debug("ItemServiceImpl.read");

        return repository.getReferenceById(itemId);
    }

    @Override
    public void modify(Item item) throws Exception {

        log.debug("ItemServiceImpl.modify");

        Item itemEntity = repository.getReferenceById(item.getItemId());

        itemEntity.setItemName(item.getItemName());
        itemEntity.setPrice(item.getPrice());
        itemEntity.setDescription(item.getDescription());
        itemEntity.setPictureUrl(item.getPictureUrl());
        itemEntity.setPreviewUrl(item.getPreviewUrl());

        repository.save(itemEntity);
    }

    @Override
    public void remove(Long itemId) throws Exception {

        log.debug("ItemServiceImpl.remove");

        repository.deleteById(itemId);
    }

    @Override
    public List<Item> list() throws Exception {

        log.debug("ItemServiceImpl.list");

        return repository.findAll(Sort.by(Sort.Direction.DESC, "itemId"));
    }

    @Override
    public String getPreview(Long itemId) throws Exception {

        log.debug("ItemServiceImpl.getPreview");

        Item item = repository.getReferenceById(itemId);
        return item.getPreviewUrl();
    }

    @Override
    public String getPicture(Long itemId) throws Exception {

        log.debug("ItemServiceImpl.getPicture");

        Item item = repository.getReferenceById(itemId);
        return item.getPictureUrl();
    }
}
