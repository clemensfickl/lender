import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './lending.reducer';
import { ILending } from 'app/shared/model/lending.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILendingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class LendingDetail extends React.Component<ILendingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { lendingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="lenderApp.lending.detail.title">Lending</Translate> [<b>{lendingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="start">
                <Translate contentKey="lenderApp.lending.start">Start</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={lendingEntity.start} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="plannedEnd">
                <Translate contentKey="lenderApp.lending.plannedEnd">Planned End</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={lendingEntity.plannedEnd} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="end">
                <Translate contentKey="lenderApp.lending.end">End</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={lendingEntity.end} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="informedAboutEnd">
                <Translate contentKey="lenderApp.lending.informedAboutEnd">Informed About End</Translate>
              </span>
            </dt>
            <dd>{lendingEntity.informedAboutEnd ? 'true' : 'false'}</dd>
            <dt>
              <span id="cost">
                <Translate contentKey="lenderApp.lending.cost">Cost</Translate>
              </span>
            </dt>
            <dd>{lendingEntity.cost}</dd>
            <dt>
              <span id="paid">
                <Translate contentKey="lenderApp.lending.paid">Paid</Translate>
              </span>
            </dt>
            <dd>{lendingEntity.paid ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="lenderApp.lending.borrower">Borrower</Translate>
            </dt>
            <dd>{lendingEntity.borrowerLogin ? lendingEntity.borrowerLogin : ''}</dd>
            <dt>
              <Translate contentKey="lenderApp.lending.item">Item</Translate>
            </dt>
            <dd>{lendingEntity.itemName ? lendingEntity.itemName : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/lending" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/lending/${lendingEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ lending }: IRootState) => ({
  lendingEntity: lending.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LendingDetail);
