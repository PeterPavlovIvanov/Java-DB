package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.constants.GlobalConstants;
import softuni.exam.domain.dtos.PlayerSeedDto;
import softuni.exam.domain.entities.Player;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final Gson gson;
    private final TeamService teamService;

    public PlayerServiceImpl(PlayerRepository playerRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, Gson gson, TeamService teamService) {
        this.playerRepository = playerRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.teamService = teamService;
    }

    @Override
    public String importPlayers() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        PlayerSeedDto[] players =
                this.gson.fromJson(new FileReader(GlobalConstants.PLAYERS_FILE_PATH), PlayerSeedDto[].class);

        for (PlayerSeedDto playerSeedDto : players) {
            if (this.validatorUtil.isValid(playerSeedDto)) {

                if (this.playerRepository.findByFirstNameAndLastName(playerSeedDto.getFirstName(), playerSeedDto.getLastName()) == null) {
                    Player player = this.modelMapper.map(playerSeedDto, Player.class);

                    this.playerRepository.saveAndFlush(player);
                    sb.append("Successfully imported player: ")
                            .append(playerSeedDto.getFirstName())
                            .append(" ")
                            .append(playerSeedDto.getLastName());
                } else {
                    sb.append("Already in db");
                }

            } else {
                sb.append("Invalid player");
            }
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return Files.readString(Path.of(GlobalConstants.PLAYERS_FILE_PATH));
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        List<Player> players = this.playerRepository.findAllBySalaryGreaterThanOrderBySalaryDesc(new BigDecimal(100000));
        StringBuilder result = new StringBuilder();

        for (Player player : players) {
            result.append(String.format("Player name: %s %s\r\n\tNumber: %d\r\n\tSalary: %.2f\r\n\tTeam: %s\r\n",
                    player.getFirstName(), player.getLastName(), player.getNumber(),
                    player.getSalary(), player.getTeam().getName()));
        }

        return result.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        List<Player> players = this.playerRepository.findAllByTeamNameOrderById("North Hub");
        System.out.println("");
        StringBuilder result = new StringBuilder("Team North Hub\r\n");
        for (Player player : players) {
            result.append("\t");
            result.append("Player name: ").append(player.getFirstName()).append(" ").append(player.getLastName()).append(" - ").append(player.getPosition());
            result.append("\r\n\t");
            result.append("Number: ").append(player.getNumber()).append("\r\n");
        }

        return result.toString();
    }


}
