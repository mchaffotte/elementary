import { Card, Container, makeStyles } from "@material-ui/core";
import { FunctionComponent } from "react";

import { LoadingSection } from "./LoadingSection";
import { SectionContent } from "./SectionContent";
import { Section } from "../../api";

const useStyles = makeStyles({
  root: {
    minWidth: 275,
  },
});

type SectionViewProps = {
  section: Section | null;
  onTurnTo: Function;
};

export const SectionView: FunctionComponent<SectionViewProps> = ({
  section,
  onTurnTo,
}) => {
  const classes = useStyles();

  return (
    <Container maxWidth="sm">
      <Card className={classes.root} variant="outlined">
        {!section && <LoadingSection />}
        <SectionContent section={section} onTurnTo={onTurnTo} />
      </Card>
    </Container>
  );
};
