package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {

    @Autowired
    private TrelloMapper trelloMapper;
    private TrelloCard trelloCard;
    private TrelloCardDto trelloCardDto;
    private TrelloList trelloList;
    private TrelloListDto trelloListDto;
    private TrelloBoard trelloBoard;
    private TrelloBoardDto trelloBoardDto;

    @Before
    public void init() {
        trelloCard = new TrelloCard("card1", "cardDescription1", "top", "cardId1");
        trelloCardDto = new TrelloCardDto("cardDto1", "cardDtoDescription1", "bottom", "cardDtoId1");
        trelloList = new TrelloList("trelloListId1", "trelloList1", false);
        trelloListDto = new TrelloListDto("trelloListDtoId1", "trelloListDto1", false);
        trelloBoard = new TrelloBoard("trelloBoardId1", "trelloBoard1", new ArrayList<>());
        trelloBoardDto = new TrelloBoardDto("trelloBoardDtoId1", "trelloBoardDto1", new ArrayList<>());
    }

    @Test
    public void mapToBoardsTest() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);
        //When
        List<TrelloBoard> mappedTrelloBoardList = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals(trelloBoardDtos.get(0).getId(), mappedTrelloBoardList.get(0).getId());
        assertEquals(trelloBoardDtos.get(0).getName(), mappedTrelloBoardList.get(0).getName());
        assertEquals(trelloBoardDtos.get(0).getLists(), mappedTrelloBoardList.get(0).getLists());
    }

    @Test
    public void mapToBoardsDtoTest() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);
        //When
        List<TrelloBoardDto> mappedTrelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals(trelloBoards.get(0).getId(), mappedTrelloBoardDtos.get(0).getId());
        assertEquals(trelloBoards.get(0).getName(), mappedTrelloBoardDtos.get(0).getName());
        assertEquals(trelloBoards.get(0).getLists(), mappedTrelloBoardDtos.get(0).getLists());
    }

    @Test
    public void mapToListTest() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(trelloListDto);
        //When
        List<TrelloList> mappedTrelloLists = trelloMapper.mapToList(trelloListDtos);
        //Then
        assertEquals(trelloListDtos.get(0).getId(), mappedTrelloLists.get(0).getId());
        assertEquals(trelloListDtos.get(0).getName(), mappedTrelloLists.get(0).getName());
        assertEquals(trelloListDtos.get(0).isClosed(), mappedTrelloLists.get(0).isClosed());
    }

    @Test
    public void mapToListDtoTest() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList);
        //When
        List<TrelloListDto> mappedTrelloListDtos = trelloMapper.mapToListDto(trelloLists);
        //Then
        assertEquals(trelloLists.get(0).getId(), mappedTrelloListDtos.get(0).getId());
        assertEquals(trelloLists.get(0).getName(), mappedTrelloListDtos.get(0).getName());
        assertEquals(trelloLists.get(0).isClosed(), mappedTrelloListDtos.get(0).isClosed());
    }

    @Test
    public void mapToCardTest() {
        //Given
        //When
        TrelloCard mappedTrelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals(trelloCardDto.getName(), mappedTrelloCard.getName());
        assertEquals(trelloCardDto.getDescription(), mappedTrelloCard.getDescription());
        assertEquals(trelloCardDto.getPos(), mappedTrelloCard.getPos());
        assertEquals(trelloCardDto.getListId(), mappedTrelloCard.getListId());
    }

    @Test
    public void mapToCardDtoTest() {
        //Given
        //When
        TrelloCardDto mappedTrelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals(trelloCard.getName(), mappedTrelloCardDto.getName());
        assertEquals(trelloCard.getDescription(), mappedTrelloCardDto.getDescription());
        assertEquals(trelloCard.getPos(), mappedTrelloCardDto.getPos());
        assertEquals(trelloCard.getListId(), mappedTrelloCardDto.getListId());
    }
}
