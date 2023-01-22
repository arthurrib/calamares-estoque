import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ProdutoFornecedorService } from '../service/produto-fornecedor.service';

import { ProdutoFornecedorComponent } from './produto-fornecedor.component';

describe('ProdutoFornecedor Management Component', () => {
  let comp: ProdutoFornecedorComponent;
  let fixture: ComponentFixture<ProdutoFornecedorComponent>;
  let service: ProdutoFornecedorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'produto-fornecedor', component: ProdutoFornecedorComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [ProdutoFornecedorComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(ProdutoFornecedorComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProdutoFornecedorComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(ProdutoFornecedorService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.produtoFornecedors?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to produtoFornecedorService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getProdutoFornecedorIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getProdutoFornecedorIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});
