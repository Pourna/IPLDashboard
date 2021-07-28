package io.ipldashboard.data;
import io.ipldashboard.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;


public class MatchDataProcessor implements ItemProcessor<MatchInput, Match>{
    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        final String firstName = matchInput.getCity().toUpperCase();
        final String lastName = matchInput.getDate().toUpperCase();

        final Match transformedMatch = new Match();
        transformedMatch.setId(Long.parseLong(matchInput.getId()));
        transformedMatch.setCity(matchInput.getCity());
        transformedMatch.setDate(LocalDate.parse(matchInput.getDate()));
        transformedMatch.setEliminator(matchInput.getEliminator());
        transformedMatch.setPlayerOfMatch(matchInput.getPlayer_of_match());
        transformedMatch.setVenue(matchInput.getVenue());
        transformedMatch.setTossWinner(matchInput.getToss_winner());
        transformedMatch.setTossDecision(matchInput.getToss_decision());
        transformedMatch.setWinner(matchInput.getWinner());

        if("bat".equals(matchInput.getToss_decision())){
            transformedMatch.setTeam1(matchInput.getToss_winner());
            transformedMatch.setTeam2(matchInput.getToss_winner()
                    .equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1());
        }
        else {
            transformedMatch.setTeam2(matchInput.getToss_winner());
            transformedMatch.setTeam1(matchInput.getToss_winner()
                    .equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1());
        }

        transformedMatch.setMethod(matchInput.getMethod());
        transformedMatch.setResult(matchInput.getResult());
        transformedMatch.setResultMargin(matchInput.getResult_margin());
        transformedMatch.setUmpire1(matchInput.getUmpire1());
        transformedMatch.setUmpire2(matchInput.getUmpire2());

        log.info("Converting (" + matchInput + ") into (" + transformedMatch + ")");

        return transformedMatch;
    }
}


