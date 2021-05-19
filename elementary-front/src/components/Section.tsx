import {
  Card,
  CardActions,
  CardContent,
  Container,
  CssBaseline,
  IconButton,
  List,
  ListItem,
  ListItemSecondaryAction,
  ListItemText,
  Typography,
  makeStyles,
} from "@material-ui/core";
import NavigateNextIcon from "@material-ui/icons/NavigateNext";
import { FunctionComponent } from "react";

import SectionText from "./SectionText";
import { Section } from "../api";

const useStyles = makeStyles({
  root: {
    minWidth: 275,
  },
  title: {
    fontSize: 20,
    textAlign: "center",
  },
});

type SectionViewProps = {
  section: Section | null;
  storyId: number;
  onTurnTo: Function;
};

const SectionView: FunctionComponent<SectionViewProps> = ({
  section,
  storyId,
  onTurnTo,
}) => {
  const classes = useStyles();

  if (!section) {
    return null;
  }
  return (
    <>
      <CssBaseline />
      <Container maxWidth="sm">
        <Card className={classes.root} variant="outlined">
          <CardContent>
            <Typography
              className={classes.title}
              color="textSecondary"
              gutterBottom
            >
              {section.reference}
            </Typography>
            <SectionText text={section.text} storyId={storyId} />
          </CardContent>
          <CardActions>
            <List>
              {section.actions.map((action, index) => (
                <ListItem key={index}>
                  <ListItemText primary={action.description} />
                  <ListItemSecondaryAction>
                    <IconButton
                      edge="end"
                      aria-label="next"
                      onClick={() => {
                        onTurnTo(index);
                      }}
                    >
                      <NavigateNextIcon />
                    </IconButton>
                  </ListItemSecondaryAction>
                </ListItem>
              ))}
            </List>
          </CardActions>
        </Card>
      </Container>
    </>
  );
};

export default SectionView;
