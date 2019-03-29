import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Lending from './lending';
import LendingDetail from './lending-detail';
import LendingUpdate from './lending-update';
import LendingDeleteDialog from './lending-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LendingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LendingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LendingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Lending} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={LendingDeleteDialog} />
  </>
);

export default Routes;
