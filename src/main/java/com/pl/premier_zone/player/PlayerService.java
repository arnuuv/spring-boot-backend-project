package com.pl.premier_zone.player;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class PlayerService {
    private final PlayerRepository playerRepository;
    
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }
    public List<Player> getPlayersFromTeam(String teamName) {
        return playerRepository.findAll().stream()
            .filter(player -> teamName.equals(player.getTeam()))
            .collect(Collectors.toList());
    }
    public List<Player> getPlayersByName(String searchText){
      return playerRepository.findAll().stream()
        .filter(player -> player.getName().toLowerCase().contains(searchText.toLowerCase()))
        .collect(Collectors.toList());
    }

    public List<Player> getPlayersByPos(String searchText){
      return playerRepository.findAll().stream()
        .filter(player -> player.getPos().toLowerCase().contains(searchText.toLowerCase()))
        .collect(Collectors.toList());
    }

    public List<Player> getPlayersByNation(String searchText){
      return playerRepository.findAll().stream()
        .filter(player -> player.getNation().toLowerCase().contains(searchText.toLowerCase()))
        .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPosition (String team,String position){
      return playerRepository.findAll().stream()
        .filter(player -> team.equals(player.getTeam()) && position.equals(player.getPos()))
        .collect(Collectors.toList());
    }

    public Player addPlayer(Player player){
      return playerRepository.save(player);
    }

    public Player updatePlayer(Player updatedPlayer){
      Optional<Player> existingPlayer = playerRepository.findByName(updatedPlayer.getName());
      if (existingPlayer.isPresent()){
        Player playerToUpdate = existingPlayer.get();
        playerToUpdate.setName(updatedPlayer.getName());
        playerToUpdate.setTeam(updatedPlayer.getTeam());
        playerToUpdate.setPos(updatedPlayer.getPos());
        playerToUpdate.setNation(updatedPlayer.getNation());
        playerRepository.save(playerToUpdate);
        return playerToUpdate;
      }
      return null;
    }
    @Transactional
    public boolean deletePlayer(String playerName){
      Optional<Player> existingPlayer = playerRepository.findByName(playerName);
      if (existingPlayer.isPresent()){
        playerRepository.delete(existingPlayer.get());
        return true;
      }
      return false;
    }
}



