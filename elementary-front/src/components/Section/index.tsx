import { Card, Container, CssBaseline, makeStyles } from "@material-ui/core";
import { FunctionComponent } from "react";

import { LoadingSection } from "./LoadingSection";
import { SectionContent } from "./SectionContent";
import { Section } from "../../api";

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

export const SectionView: FunctionComponent<SectionViewProps> = ({
  section,
  storyId,
  onTurnTo,
}) => {
  const classes = useStyles();

  return (
    <>
      <CssBaseline />
      <Container maxWidth="sm">
        <Card className={classes.root} variant="outlined">
          {!section && <LoadingSection />}
          <SectionContent
            section={section}
            storyId={storyId}
            onTurnTo={onTurnTo}
          />
        </Card>
      </Container>
    </>
  );
};
