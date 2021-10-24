import { Container } from "@mui/material";
import { FunctionComponent } from "react";

import { LoadingSection } from "./LoadingSection";
import { SectionContent } from "./SectionContent";
import { PageCard } from "../CardPage";
import { Section } from "../../api";

type SectionViewProps = {
  section: Section | null;
  onTurnTo: Function;
};

export const SectionView: FunctionComponent<SectionViewProps> = ({
  section,
  onTurnTo,
}) => {
  return (
    <Container maxWidth="sm">
      <PageCard variant="outlined">
        {!section && <LoadingSection />}
        <SectionContent section={section} onTurnTo={onTurnTo} />
      </PageCard>
    </Container>
  );
};
