import axios from 'axios';
import {
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ILending, defaultValue } from 'app/shared/model/lending.model';

export const ACTION_TYPES = {
  FETCH_LENDING_LIST: 'lending/FETCH_LENDING_LIST',
  FETCH_LENDING: 'lending/FETCH_LENDING',
  CREATE_LENDING: 'lending/CREATE_LENDING',
  UPDATE_LENDING: 'lending/UPDATE_LENDING',
  DELETE_LENDING: 'lending/DELETE_LENDING',
  RESET: 'lending/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILending>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type LendingState = Readonly<typeof initialState>;

// Reducer

export default (state: LendingState = initialState, action): LendingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_LENDING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LENDING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LENDING):
    case REQUEST(ACTION_TYPES.UPDATE_LENDING):
    case REQUEST(ACTION_TYPES.DELETE_LENDING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_LENDING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LENDING):
    case FAILURE(ACTION_TYPES.CREATE_LENDING):
    case FAILURE(ACTION_TYPES.UPDATE_LENDING):
    case FAILURE(ACTION_TYPES.DELETE_LENDING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_LENDING_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_LENDING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LENDING):
    case SUCCESS(ACTION_TYPES.UPDATE_LENDING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LENDING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/lendings';

// Actions

export const getEntities: ICrudGetAllAction<ILending> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LENDING_LIST,
    payload: axios.get<ILending>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ILending> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LENDING,
    payload: axios.get<ILending>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILending> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LENDING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ILending> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LENDING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILending> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LENDING,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
