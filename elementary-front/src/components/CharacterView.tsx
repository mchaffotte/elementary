import {
  Card,
  CardContent,
  Container,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Typography,
  makeStyles,
} from "@material-ui/core";
import { FunctionComponent } from "react";

import { Character, Money } from "../api";

const useStyles = makeStyles({
  root: {
    minWidth: 275,
  },
});

type CharacterViewProps = {
  character: Character | null;
};

const getMoneyLabel = (money: Money): string => {
  return `£${money.pounds} ${money.shillings}s ${money.pence}d`;
};

export const CharacterView: FunctionComponent<CharacterViewProps> = ({
  character,
}) => {
  const classes = useStyles();

  if (!character) {
    return null;
  }
  return (
    <Container maxWidth="sm">
      <Card className={classes.root} variant="outlined">
        {/* {!character && <LoadingSection />} */}
        <CardContent>
          <Typography variant="h5" component="h2">
            {character.name}
          </Typography>
          <Typography>Money: {getMoneyLabel(character.money)}</Typography>
          <Table aria-label="skills">
            <TableHead>
              <TableRow>
                <TableCell>Skill</TableCell>
                <TableCell align="right">Bonus</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {character.skills.map((skill) => (
                <TableRow key={skill.name}>
                  <TableCell component="th" scope="row">
                    {skill.name}
                  </TableCell>
                  <TableCell align="right">{skill.value}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </Container>
  );
};
